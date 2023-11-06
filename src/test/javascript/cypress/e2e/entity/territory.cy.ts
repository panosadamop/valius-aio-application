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

describe('Territory e2e test', () => {
  const territoryPageUrl = '/territory';
  const territoryPageUrlPattern = new RegExp('/territory(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const territorySample = { value: 'Forward solutions navigate', language: 'GREEK' };

  let territory;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/territories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/territories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/territories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (territory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/territories/${territory.id}`,
      }).then(() => {
        territory = undefined;
      });
    }
  });

  it('Territories menu should load Territories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('territory');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Territory').should('exist');
    cy.url().should('match', territoryPageUrlPattern);
  });

  describe('Territory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(territoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Territory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/territory/new$'));
        cy.getEntityCreateUpdateHeading('Territory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', territoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/territories',
          body: territorySample,
        }).then(({ body }) => {
          territory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/territories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/territories?page=0&size=20>; rel="last",<http://localhost/api/territories?page=0&size=20>; rel="first"',
              },
              body: [territory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(territoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Territory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('territory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', territoryPageUrlPattern);
      });

      it('edit button click should load edit Territory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Territory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', territoryPageUrlPattern);
      });

      it('edit button click should load edit Territory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Territory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', territoryPageUrlPattern);
      });

      it('last delete button click should delete instance of Territory', () => {
        cy.intercept('GET', '/api/territories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('territory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', territoryPageUrlPattern);

        territory = undefined;
      });
    });
  });

  describe('new Territory page', () => {
    beforeEach(() => {
      cy.visit(`${territoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Territory');
    });

    it('should create an instance of Territory', () => {
      cy.get(`[data-cy="value"]`).type('Proactive Ways eco-centric').should('have.value', 'Proactive Ways eco-centric');

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        territory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', territoryPageUrlPattern);
    });
  });
});
