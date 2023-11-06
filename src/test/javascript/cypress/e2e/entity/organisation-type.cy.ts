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

describe('OrganisationType e2e test', () => {
  const organisationTypePageUrl = '/organisation-type';
  const organisationTypePageUrlPattern = new RegExp('/organisation-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const organisationTypeSample = { value: 'Nevada Communications', language: 'ENGLISH' };

  let organisationType;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/organisation-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/organisation-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/organisation-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (organisationType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/organisation-types/${organisationType.id}`,
      }).then(() => {
        organisationType = undefined;
      });
    }
  });

  it('OrganisationTypes menu should load OrganisationTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('organisation-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('OrganisationType').should('exist');
    cy.url().should('match', organisationTypePageUrlPattern);
  });

  describe('OrganisationType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(organisationTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create OrganisationType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/organisation-type/new$'));
        cy.getEntityCreateUpdateHeading('OrganisationType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', organisationTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/organisation-types',
          body: organisationTypeSample,
        }).then(({ body }) => {
          organisationType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/organisation-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/organisation-types?page=0&size=20>; rel="last",<http://localhost/api/organisation-types?page=0&size=20>; rel="first"',
              },
              body: [organisationType],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(organisationTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details OrganisationType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('organisationType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', organisationTypePageUrlPattern);
      });

      it('edit button click should load edit OrganisationType page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OrganisationType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', organisationTypePageUrlPattern);
      });

      it('edit button click should load edit OrganisationType page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OrganisationType');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', organisationTypePageUrlPattern);
      });

      it('last delete button click should delete instance of OrganisationType', () => {
        cy.intercept('GET', '/api/organisation-types/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('organisationType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', organisationTypePageUrlPattern);

        organisationType = undefined;
      });
    });
  });

  describe('new OrganisationType page', () => {
    beforeEach(() => {
      cy.visit(`${organisationTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('OrganisationType');
    });

    it('should create an instance of OrganisationType', () => {
      cy.get(`[data-cy="value"]`).type('bluetooth Computer').should('have.value', 'bluetooth Computer');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        organisationType = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', organisationTypePageUrlPattern);
    });
  });
});
