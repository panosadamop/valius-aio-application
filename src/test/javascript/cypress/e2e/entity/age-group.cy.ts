import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('AgeGroup e2e test', () => {
  const ageGroupPageUrl = '/age-group';
  const ageGroupPageUrlPattern = new RegExp('/age-group(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const ageGroupSample = { value: 'generate Dynamic', language: 'ENGLISH' };

  let ageGroup;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/age-groups+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/age-groups').as('postEntityRequest');
    cy.intercept('DELETE', '/api/age-groups/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (ageGroup) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/age-groups/${ageGroup.id}`,
      }).then(() => {
        ageGroup = undefined;
      });
    }
  });

  it('AgeGroups menu should load AgeGroups page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('age-group');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AgeGroup').should('exist');
    cy.url().should('match', ageGroupPageUrlPattern);
  });

  describe('AgeGroup page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(ageGroupPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AgeGroup page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/age-group/new$'));
        cy.getEntityCreateUpdateHeading('AgeGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ageGroupPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/age-groups',
          body: ageGroupSample,
        }).then(({ body }) => {
          ageGroup = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/age-groups+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/age-groups?page=0&size=20>; rel="last",<http://localhost/api/age-groups?page=0&size=20>; rel="first"',
              },
              body: [ageGroup],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(ageGroupPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AgeGroup page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('ageGroup');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ageGroupPageUrlPattern);
      });

      it('edit button click should load edit AgeGroup page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AgeGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ageGroupPageUrlPattern);
      });

      it('edit button click should load edit AgeGroup page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AgeGroup');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ageGroupPageUrlPattern);
      });

      it('last delete button click should delete instance of AgeGroup', () => {
        cy.intercept('GET', '/api/age-groups/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('ageGroup').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ageGroupPageUrlPattern);

        ageGroup = undefined;
      });
    });
  });

  describe('new AgeGroup page', () => {
    beforeEach(() => {
      cy.visit(`${ageGroupPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AgeGroup');
    });

    it('should create an instance of AgeGroup', () => {
      cy.get(`[data-cy="value"]`).type('application reinvent').should('have.value', 'application reinvent');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        ageGroup = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', ageGroupPageUrlPattern);
    });
  });
});
